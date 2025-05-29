import { createCookie, createCookieSessionStorage } from "@remix-run/node";

export const sessionStorage = () => {
  const cookie = createCookie("session", {
    httpOnly: true,
    secure: false,
    sameSite: "lax",
    path: "/",
    maxAge: 60 * 60 * 4,
    secrets: ["2cd0bf540a7603589a943770279fed79"],
  });

  return createCookieSessionStorage({ cookie });
}