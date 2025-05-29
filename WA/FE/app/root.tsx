import { Links, Meta, Outlet, Scripts, ScrollRestoration } from "@remix-run/react";
import type { LinksFunction } from "@remix-run/node";
import Tailwind from "./tailwind.css?url";
import FontAwesome from "./FontAwesome/css/all.css?url";
import Stylesheet from "./VAK.css?url";
import AppleTouchIcon from "./IMG/Favicon/Apple.png";
import Favicon32 from "./IMG/Favicon/Favicon_32.png";
import Favicon16 from "./IMG/Favicon/Favicon_16.png";
import Favicon from "./IMG/Favicon/Favicon.ico";
import Safari from "./IMG/Favicon/Safari.svg";
import Manifest from "./IMG/Favicon/site.webmanifest";
import Header from "./components/Header";
import Footer from "./components/Footer";

export const links: LinksFunction = () => [
  { rel: "stylesheet", href: Tailwind },
  { rel: "stylesheet", href: FontAwesome },
  { rel: "stylesheet", href: Stylesheet },
  { rel: "apple-touch-icon", sizes: "180x180", href: AppleTouchIcon },
  { rel: "manifest", href: Manifest },
  { rel: "icon", type: "image/png", sizes: "32x32", href: Favicon32 },
  { rel: "icon", type: "image/png", sizes: "16x16", href: Favicon16 },
  { rel: "shortcut icon", href: Favicon },
  { rel: "mask-icon", href: Safari, color: "#fff" },
  { rel: "preconnect", href: "https://fonts.googleapis.com" },
  { rel: "preconnect", href: "https://fonts.gstatic.com", crossOrigin: "anonymous" },
  { rel: "stylesheet", href: "https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" },
];

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
        <meta httpEquiv="X-UA-Compatible" content="ie=edge" />
        <meta name="msapplication-TileColor" content="#fff" />
        <meta name="msapplication-config" content="/IMG/Favicon/browserconfig.xml" />
        <title>VAK</title>
        <Meta />
        <Links />
      </head>
      <body>
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return (
    <>
      <Header />
      <Outlet />
      <Footer />
    </>
  )
}