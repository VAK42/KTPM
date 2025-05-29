import { Link, useLocation } from "@remix-run/react";

export default function Header() {
  const lct = useLocation();
  return (
    <header
      className={`w-full h-12 text-white flex fixed z-50 ${lct.pathname === "/" ? "bg-[rgba(0,0,0,0.8)]" : "bg-black"}`}
    >
      <div className="w-1/2">
        <div className="w-1/2 h-full flex justify-center items-center">
          <img src="/app/IMG/VAK.png" alt="VAK" className="max-h-full" />
        </div>
      </div>
      <div className="w-1/2 flex">
        <div className="w-1/4 flex justify-center items-center">
          <Link
            to="/"
            className={`h-full flex justify-center items-center ${lct.pathname === "/" ? "text-teal-300 border-b-2 border-teal-300" : "hover:text-teal-300 anmt"}`}
          >
            <i className="fa-light fa-house mr-2"></i>
            Home
          </Link>
        </div>
        <div className="w-1/4 flex justify-center items-center">
          <Link
            to="/shop"
            className={`h-full flex justify-center items-center ${lct.pathname === "/shop" ? "text-teal-300 border-b-2 border-teal-300" : "hover:text-teal-300 anmt"}`}
          >
            <i className="fa-light fa-box-archive m-2"></i>
            Shop
          </Link>
        </div>
        <div className="w-1/4 flex justify-center items-center">
          <Link
            to="/intro"
            className={`h-full flex justify-center items-center ${lct.pathname === "/intro" ? "text-teal-300 border-b-2 border-teal-300" : "hover:text-teal-300 anmt"}`}
          >
            <i className="fa-light fa-align-justify mr-2"></i>
            Introduction
          </Link>
        </div>
        <div className="w-1/6 flex justify-center items-center">
          <Link
            to="/cart"
            className={`h-full flex justify-center items-center ${lct.pathname === "/cart" ? "text-teal-300 border-b-2 border-teal-300" : "hover:text-teal-300 anmt"}`}
          >
            <i className="fa-light fa-cart-shopping mx-2"></i>
          </Link>
        </div>
        <div className="w-1/12 flex justify-center items-center">
          <Link
            to="/user"
            className={`h-full flex justify-center items-center ${lct.pathname === "/user" ? "text-teal-300 border-b-2 border-teal-300" : "hover:text-teal-300 anmt"}`}
          >
            <i className="fa-light fa-user mx-2"></i>
          </Link>
        </div>
      </div>
    </header>
  )
}