import { Link, useLoaderData, useNavigate, useSearchParams } from "@remix-run/react";
import { LoaderFunction } from "@remix-run/node";
import { useState, useMemo } from "react";
import { Button, Input } from "@material-tailwind/react";
import axios from "axios";

type Product = {
  id: string;
  name: string;
  price: number;
  quantity: number;
};

export const loader: LoaderFunction = async ({ request }) => {
  try {
    const url = new URL(request.url);
    const search = url.searchParams.get("search") || "";
    const field = url.searchParams.get("sort") || "id";
    const direction = url.searchParams.get("order") || "asc";
    const page = parseInt(url.searchParams.get("page") || "1", 10);
    const limit = 10;
    const response = await axios.get("http://localhost:8080/pd/paginated", { params: { search, sort: field, order: direction, page, limit } });
    return new Response(JSON.stringify(response.data), { status: 200, headers: { "Content-Type": "application/json" } });
  } catch (error) {
    return new Response(JSON.stringify({ data: [], total: 0, page: 1, limit: 20 }), { status: 200, headers: { "Content-Type": "application/json" } });
  }
};

export default function SP() {
  const { data, total, page, limit } = useLoaderData<{ data: Product[]; total: number; page: number; limit: number; }>();
  const [search, setSearch] = useState<string>("");
  const [showSort, setShowSort] = useState<boolean>(false);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const handleSearch = () => {
    navigate(`/shop?search=${search}&page=1`);
  };
  const sort = [
    { value: "id", order: "asc", label: "Mã SP Tăng Dần" },
    { value: "id", order: "desc", label: "Mã SP Giảm Dần" },
    { value: "price", order: "asc", label: "Giá SP Tăng Dần" },
    { value: "price", order: "desc", label: "Giá SP Giảm Dần" },
  ];
  const sumPages = Math.ceil(total / limit);
  const pagination = useMemo(() => {
    const pages = [];
    if (sumPages > 1) {
      if (page > 1) pages.push(page - 1);
      pages.push(page);
      if (page < sumPages) pages.push(page + 1);
    }
    return [...new Set(pages)];
  }, [sumPages, page]);
  return (
    <main className="w-4/5 min-h-screen pt-12 m-auto">
      <img src="/app/IMG/VAK.png" alt="VAK" className="w-1/12 py-10 m-auto" />
      <div className="border-t-2 border-black">
        <div className="flex p-2">
          <div className="w-5/6">
            <Input
              value={search}
              placeholder="Product Name"
              onChange={(e) => setSearch(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") handleSearch();
              }}
              className="border-2 border-black rounded-none"
            >
              <Input.Icon>
                <i className="fa-light fa-magnifying-glass"></i>
              </Input.Icon>
            </Input>
          </div>
          <div className="w-1/6 inline-block">
            <Button
              variant="outline"
              className="h-full border-2 border-black float-left rounded-none"
              onClick={handleSearch}
            >
              <i className="fa-light fa-magnifying-glass"></i>
            </Button>
            <Button
              variant="outline"
              className={`h-full border-2 border-black float-right ${showSort ? "text-white bg-black" : ""}`}
              onClick={() => setShowSort(!showSort)}
            >
              {showSort ? (
                <i className="fa-solid fa-xmark"></i>
              ) : (
                <i className="fa-light fa-filter"></i>
              )}
            </Button>
            {showSort && (
              <div className="bg-white border-2 border-black flex flex-col absolute right-4">
                {sort.map((opt) => (
                  <Link
                    key={opt.value + opt.order}
                    to={`/shop?sort=${opt.value}&order=${opt.order}&search=${search}&page=1`}
                    className="p-1 hover:text-white hover:bg-black"
                    onClick={() => setShowSort(false)}
                  >
                    {opt.label}
                  </Link>
                ))}
              </div>
            )}
          </div>
        </div>
        {data.length !== 0 ? (
          <div className="grid grid-cols-5 gap-2 p-2 overflow-auto">
            {data.map((pd) => (
              <Link
                to={`/shop/${pd.id}`}
                key={pd.id}
                className="h-[20vw] border-2 border-black"
              >
                <div className="h-2/3 border-b-2 border-black">
                  <img
                    src="/app/IMG/VAK_00.png"
                    alt="VAK"
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
        <div aria-live="polite" className="flex justify-center my-4">
          {page > 1 && (
            <div className="mx-2">
              <Link to={`/shop?sort=${searchParams.get("sort") || "id"}&order=${searchParams.get("order") || "asc"}&search=${search}&page=1`}>
                <i className="fa-light fa-chevrons-left mr-4"></i>
              </Link>
              <Link to={`/shop?sort=${searchParams.get("sort") || "id"}&order=${searchParams.get("order") || "asc"}&search=${search}&page=${page - 1}`}>
                <i className="fa-light fa-arrow-left"></i>
              </Link>
            </div>
          )}
          {pagination.map((num) => (
            <div key={num} className="mx-2">
              <Link
                to={`/shop?sort=${searchParams.get("sort") || "id"}&order=${searchParams.get("order") || "asc"}&search=${search}&page=${num}`}
                className={`p-2 rounded ${page === num ? "text-white bg-black" : "bg-gray-300"}`}
              >
                {num}
              </Link>
            </div>
          ))}
          {page < sumPages && (
            <div className="mx-2">
              <Link to={`/shop?sort=${searchParams.get("sort") || "id"}&order=${searchParams.get("order") || "asc"}&search=${search}&page=${page + 1}`}>
                <i className="fa-light fa-arrow-right"></i>
              </Link>
              <Link to={`/shop?sort=${searchParams.get("sort") || "id"}&order=${searchParams.get("order") || "asc"}&search=${search}&page=${sumPages}`}>
                <i className="fa-light fa-chevrons-right ml-4"></i>
              </Link>
            </div>
          )}
        </div>
      </div>
    </main>
  )
}