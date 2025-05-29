import { Button } from "@material-tailwind/react";
import { ActionFunction, LoaderFunction, redirect } from "@remix-run/node";
import { useLoaderData, Form, useSearchParams, useNavigate } from "@remix-run/react";
import { useEffect } from "react";
import { sessionStorage } from "~/utils/session.server";

type LoaderData = {
  email: string;
  username: string;
};

export const loader: LoaderFunction = async ({ request }: { request: Request }) => {
  const session = await sessionStorage().getSession(request.headers.get("Cookie"));
  const usr = session.get("user");
  if (!usr) return redirect("/login");
  return new Response(JSON.stringify({ email: session.get("email"), username: session.get("username") }), { status: 200, headers: { "Content-Type": "application/json" } });
};

export const action: ActionFunction = async ({ request }: { request: Request }) => {
  const session = await sessionStorage().getSession(request.headers.get("Cookie"));
  return redirect("/user?logout=success", { headers: { "Set-Cookie": await sessionStorage().destroySession(session) } });
};

export default function UserPage() {
  const { email, username } = useLoaderData<LoaderData>();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  useEffect(() => {
    if (searchParams.get("logout") === "success") {
      localStorage.removeItem("pd");
      navigate("/login");
    }
  }, [searchParams]);
  return (
    <main className="w-4/5 min-h-screen pt-12 m-auto">
      <div
        className="text-center text-4xl before:content-[''] before:grow before:m-auto before:border-b-2 before:border-black after:content-[''] after:grow after:m-auto after:border-b-2 after:border-black flex justify-center items-center"
      >
        <i className="fa-light fa-circle-user text-8xl p-4"></i>
      </div>
      <div className="max-w-fit m-auto">
        <h2 className="text-center text-2xl border-2 border-black mt-10">
          My Info
        </h2>
        <div className="h-fit border-2 border-black p-4">
          <div>
            Username:<span className="pl-4 float-right">{username}</span>
          </div>
          <div>
            Email:<span className="pl-4 float-right">{email}</span>
          </div>
        </div>
        <div className="flex justify-center items-center mt-4">
          <Form method="post" className="inline">
            <input type="hidden" name="_action" value="logout" />
            <Button type="submit" variant="outline" className="border-2 border-black mx-2">LOGOUT</Button>
          </Form>
        </div>
      </div>
    </main>
  )
}