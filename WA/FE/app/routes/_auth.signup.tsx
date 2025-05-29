import { ActionFunction, redirect } from "@remix-run/node";
import { Form, Link, useActionData } from "@remix-run/react";
import { useEffect, useState } from "react";
import { Button, Input } from "@material-tailwind/react";
import { sessionStorage } from "../utils/session.server";
import axios from "axios";

type ActionData = {
  error?: string;
};

export const action: ActionFunction = async ({ request }: { request: Request }) => {
  const form = new URLSearchParams(await request.text());
  const username = form.get("username")!;
  const email = form.get("email")!;
  const password = form.get("password")!;
  const role = "user";

  try {
    const response = await axios.post("http://localhost:8080/auth/register", { username, email, password, role });
    const user = response.data;
    const session = await sessionStorage().getSession();
    session.set("user", user.id);
    session.set("email", user.email);
    session.set("username", user.username);
    return redirect("/", { headers: { "Set-Cookie": await sessionStorage().commitSession(session) } });
  } catch (err: any) {
    const error = err.response?.data?.error || "Internal Server Error!";
    return new Response(JSON.stringify({ error }), { status: err.response?.status || 500, headers: { "Content-Type": "application/json" } });
  }
};

export default function SignUp() {
  const data = useActionData<ActionData>();
  const [error, setError] = useState<string | undefined>(data?.error);

  useEffect(() => {
    if (data?.error) {
      setError(data.error);
      const timer = setTimeout(() => setError(undefined), 3000);
      return () => clearTimeout(timer);
    }
  }, [data]);

  return (
    <main className="w-4/5 min-h-screen pt-12 m-auto">
      <Form
        method="post"
        className="w-1/5 bg-slate-50 rounded-lg p-4 mt-48 mx-auto flex flex-col items-center"
      >
        <span>SIGN UP</span>
        {error && <div className="text-red-500 mb-4">{error}</div>}
        <Input
          placeholder="Username"
          name="username"
          type="text"
          className="border-b-2 border-green-800 my-2"
          required
        >
          <Input.Icon>
            <i className="fa-light fa-user"></i>
          </Input.Icon>
        </Input>
        <Input
          placeholder="Email"
          name="email"
          type="email"
          className="border-b-2 border-green-800 my-2"
          required
        >
          <Input.Icon>
            <i className="fa-light fa-envelope"></i>
          </Input.Icon>
        </Input>
        <Input
          placeholder="Password"
          name="password"
          type="password"
          className="border-b-2 border-green-800 my-2"
          required
        >
          <Input.Icon>
            <i className="fa-light fa-lock"></i>
          </Input.Icon>
        </Input>
        <div className="w-full flex justify-end">
          <Link to="/login" className="text-sm underline">Login</Link>
        </div>
        <Button variant="outline" className="border-2 border-black">
          <i className="fa-light fa-user-plus"></i>
        </Button>
      </Form>
    </main>
  )
}