import type { LoaderFunction } from "@remix-run/node";
import { Link, useLoaderData } from "@remix-run/react";
import { Carousel } from "../components/Carousel";
import axios from "axios";

type Product = {
  id: number;
  name: string;
  price: number;
  type: string;
  info: string;
  quantity: number;
};

export const loader: LoaderFunction = async () => {
  try {
    const response = await axios.get<Product[]>("http://localhost:8080/pd");
    const products = response.data;
    for (let i = products.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [products[i], products[j]] = [products[j], products[i]];
    }
    return new Response(JSON.stringify(products), { status: 200, headers: { "Content-Type": "application/json" } });
  } catch {
    return new Response(JSON.stringify([]), { status: 200, headers: { "Content-Type": "application/json" } });
  }
};

export default function Index() {
  const data: Product[] = useLoaderData();
  const img = ["/app/IMG/VAK_00.png", "/app/IMG/VAK_02.png", "/app/IMG/VAK_04.png", "/app/IMG/VAK_06.png"];
  return (
    <main className="min-h-screen">
      <Carousel img={img} />
      <img
        src="/app/IMG/VAK.png"
        alt="VAK"
        className="w-1/12 border-b-2 border-teal-500 py-4 my-4 mx-auto"
      />
      <div className="w-4/5 bg-slate-200 rounded-lg p-4 mx-auto mb-10">
        {data.length !== 0 ? (
          <div className="grid grid-cols-5 gap-4 pt-4 overflow-auto">
            {data.map((pd) => (
              <Link
                to={`/shop/${pd.id}`}
                key={pd.id}
                className="h-[20vw] border border-black"
              >
                <div className="w-full h-2/3 border-b border-black">
                  <img
                    src="/app/IMG/VAK_00.png"
                    alt={pd.name}
                    className="w-full h-full object-cover"
                    loading="lazy"
                  />
                </div>
                <div className="h-1/3 text-white bg-[rgba(0,0,0,0.8)] p-2">
                  <div className="h-1/4 text-pink-200 truncate">ID: {pd.id}</div>
                  <div className="h-1/2 text-xl truncate">{pd.name}</div>
                  <div className="h-1/4 text-green-200 text-lg float-right">
                    ${Intl.NumberFormat("en-US").format(pd.price)}
                  </div>
                </div>
              </Link>
            ))}
          </div>
        ) : (
          <div className="m-auto loader"></div>
        )}
      </div>
    </main>
  )
}