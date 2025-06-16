import { Button } from "@material-tailwind/react";
import { LoaderFunction } from "@remix-run/node";
import { useLoaderData } from "@remix-run/react";
import { useState } from "react";
import axios from "axios";

type Product = {
  id: string;
  name: string;
  price: number;
  info: string;
  category: string;
  quantity: number;
  error?: string;
};

export const loader: LoaderFunction = async ({ params }) => {
  const { id } = params;
  try {
    const response = await axios.get(`http://localhost:8080/pd/${id}`);
    const product = response.data;
    return new Response(JSON.stringify(product), { status: 200, headers: { "Content-Type": "application/json" } });
  } catch (error: any) {
    if (error.response?.status === 404) {
      return new Response(JSON.stringify({ error: "Product Not Found!" }), { status: 404, headers: { "Content-Type": "application/json" } });
    }
    return new Response(JSON.stringify({ error: "Internal Server Error!" }), { status: 500, headers: { "Content-Type": "application/json" } });
  }
};

export default function Product() {
  const data: Product = useLoaderData();
  const [info, setInfo] = useState("");
  const handleCart = async () => {
    const cart = JSON.parse(localStorage.getItem("pd") || "[]");
    const oop = cart.findIndex((item: any) => item.id === data.id);
    if (oop !== -1) {
      cart[oop].quantity += 1;
      cart[oop].total = parseFloat((cart[oop].price * cart[oop].quantity).toFixed(2));
    } else {
      cart.push({
        id: data.id,
        name: data.name,
        price: parseFloat(data.price.toFixed(2)),
        quantity: 1,
        total: parseFloat(data.price.toFixed(2)),
        tmp: data.quantity
      });
    }
    localStorage.setItem("pd", JSON.stringify(cart));
    setInfo("Added");
    setTimeout(() => setInfo(""), 3000);
  };
  return (
    <main className="w-4/5 min-h-screen pt-20 m-auto">
      {data.error ? (
        <div className="text-red-500 text-2xl text-center">{data.error}</div>
      ) : (
        <div className="flex">
          <div className="w-1/2 pt-10">
            <img
              src="/app/IMG/VAK_00.png"
              alt={data.name}
              className="w-11/12 border-2 border-black rounded"
            />
          </div>
          <div className="w-1/2 pt-10 space-y-4 relative">
            <div className="text-lg text-red-500 w-fit bg-slate-100 rounded-lg px-2">ID: {data.id}</div>
            <div className="text-4xl">{data.name}</div>
            <div className="text-2xl text-green-500">${Intl.NumberFormat("en-US").format(data.price)}</div>
            <div className="text-lg">Type: <span className="font-semibold">{data.category}</span></div>
            <div className="text-lg">
              Quantity: <span className={`font-semibold ${data.quantity === 0 ? "text-red-500" : data.quantity < 5 ? "text-yellow-500" : "text-blue-500"}`}>{data.quantity}</span>
            </div>
            <Button onClick={handleCart} className="text-xl border-2 border-black my-2">Add To Cart</Button>
            {info && (
              <div className="text-white bg-black rounded-lg px-2 absolute top-0 right-0">
                {info}
              </div>
            )}
          </div>
        </div>
      )}
      {!data.error && (
        <div className="pt-10">
          <div className="text-2xl text-black before:content-[''] before:grow before:border-b-2 before:border-black after:content-[''] after:grow after:border-b-2 after:border-black flex justify-center items-center">
            Product Info
          </div>
          <div
            className="text-xl py-4"
            dangerouslySetInnerHTML={{ __html: data.info }}
          />
        </div>
      )}
    </main>
  )
}
