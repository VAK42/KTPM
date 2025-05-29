import { Form, useLoaderData, useNavigate, useSearchParams } from "@remix-run/react";
import { ActionFunction, LoaderFunction, redirect } from "@remix-run/node";
import { useEffect, useState } from "react";
import { Button } from "@material-tailwind/react";
import { sessionStorage } from "~/utils/session.server";
import axios from "axios";

type Cart = {
  id: number;
  name: string;
  price: number;
  quantity: number;
  total: number;
  tmp: number;
};

type User = {
  id: string;
  username: string;
  email: string;
};

type LoaderData = {
  success: boolean;
  user: User | null;
};

export const loader: LoaderFunction = async ({ request }) => {
  const session = await sessionStorage().getSession(request.headers.get("Cookie"));
  const user = session.get("user");
  if (!user) {
    return new Response(JSON.stringify({ success: false, user: null }), {
      status: 401,
      headers: { "Content-Type": "application/json" },
    });
  }
  return new Response(JSON.stringify({ success: true, user }), {
    status: 200,
    headers: { "Content-Type": "application/json" },
  });
};

export const action: ActionFunction = async ({ request }) => {
  const data = new URLSearchParams(await request.text());
  const cart = JSON.parse(data.get("cart") || "[]");
  try {
    const response = await axios.post("http://localhost:8080/exp", cart);
    if (response.status === 200) {
      return redirect("/cart?checkout=success");
    }
  } catch (err: any) {
    const error = err.response?.data?.error || "Internal Server Error!";
    return new Response(JSON.stringify({ error }), { status: err.response?.status || 500, headers: { "Content-Type": "application/json" } });
  }
};

export default function Cart() {
  const data = useLoaderData<LoaderData>();
  const [cart, setCart] = useState<Cart[]>([]);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  useEffect(() => {
    if (searchParams.get("checkout") === "success") {
      localStorage.removeItem("pd");
      setCart([]);
      navigate("/");
    }
  }, [searchParams]);
  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem("pd") || "[]") as Cart[];
    const filtered = stored.filter(item => item.tmp > 0);
    const customer = filtered.map(item => ({
      ...item,
      user: data.user || null
    }));
    localStorage.setItem("pd", JSON.stringify(customer));
    setCart(customer);
  }, []);
  const updateCart = (updated: Cart[]) => {
    localStorage.setItem("pd", JSON.stringify(updated));
    setCart(updated);
  };
  const increase = (id: number) => {
    const updated = cart.map(item => {
      if (item.id === id) {
        if (item.quantity >= item.tmp) {
          return item;
        }
        const nwq = item.quantity + 1;
        return {
          ...item,
          quantity: nwq,
          total: parseFloat((item.price * nwq).toFixed(2))
        };
      }
      return item;
    });
    updateCart(updated);
  };
  const decrease = (id: number) => {
    const updated = cart.map(item => {
      if (item.id === id) {
        const nwq = item.quantity - 1;
        if (nwq <= 0) return null;
        return {
          ...item,
          quantity: nwq,
          total: parseFloat((item.price * nwq).toFixed(2))
        };
      }
      return item;
    }).filter(Boolean) as Cart[];
    updateCart(updated);
  };
  const removeItem = (id: number) => {
    const updated = cart.filter(item => item.id !== id);
    updateCart(updated);
  };
  const total = cart.reduce((sum, item) => sum + item.total, 0).toFixed(2);
  return (
    <main className="w-4/5 min-h-screen pt-12 m-auto">
      <div className="text-center text-4xl before:content-[''] before:grow before:m-auto before:border-b-2 before:border-black after:content-[''] after:grow after:m-auto after:border-b-2 after:border-black flex justify-center items-center">
        <i className="fa-light fa-cart-shopping text-8xl p-4"></i>
      </div>
      {cart.length === 0 ? (
        <div className="text-xl text-gray-500 text-center">Try Purchasing Something!</div>
      ) : (
        <>
          {data.success ? (
            <div className="text-green-500 text-center">Please Ensure Everything Before Checkout!</div>
          ) : (
            <div className="text-red-500 text-center">Please Log In To Checkout!</div>
          )}
          <div className="overflow-x-auto">
            <table className="w-full border border-black text-center">
              <thead className="bg-gray-200">
                <tr>
                  <th className="border border-black p-1">Name</th>
                  <th className="border border-black p-1">Price</th>
                  <th className="border border-black p-1">Quantity</th>
                  <th className="border border-black p-1">Total</th>
                  <th className="border border-black p-1"></th>
                </tr>
              </thead>
              <tbody>
                {cart.map(item => (
                  <tr key={item.id}>
                    <td className="border border-black p-1 truncate">{item.name}</td>
                    <td className="border border-black p-1">${item.price.toFixed(2)}</td>
                    <td className="border border-black p-1">
                      <div className="flex justify-center items-center gap-2">
                        <button onClick={() => decrease(item.id)} className="border-2 border-black rounded px-2 hover:text-white hover:bg-black">-</button>
                        {item.quantity}
                        <button onClick={() => increase(item.id)} className="border-2 border-black rounded px-2 hover:text-white hover:bg-black">+</button>
                      </div>
                    </td>
                    <td className="border border-black p-1">${item.total.toFixed(2)}</td>
                    <td className="border border-black p-1">
                      <Button onClick={() => removeItem(item.id)} className="p-1"><i className="fa-solid fa-trash-xmark"></i></Button>
                    </td>
                  </tr>
                ))}
                <tr className="bg-gray-100">
                  <td colSpan={3} className="border border-black p-1 text-right">TOTAL</td>
                  <td className="border border-black p-1 font-bold">${total}</td>
                  <td className="border border-black p-1"></td>
                </tr>
              </tbody>
            </table>
          </div>
          <Form method="post" className="mt-4 flex justify-end">
            <input type="hidden" name="cart" value={JSON.stringify(cart)} />
            <Button type="submit" disabled={!data.success} className="border-2 border-black text-sm">Checkout</Button>
          </Form>
        </>
      )}
    </main>
  )
}