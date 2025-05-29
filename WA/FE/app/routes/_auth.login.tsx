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
  const data = new URLSearchParams(await request.text());
  const email = data.get("email")!;
  const password = data.get("password")!;
  const role = "user";
  try {
    const response = await axios.post("http://localhost:8080/auth/login", { email, password, role });
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

export default function Login() {
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
        <span>LOGIN</span>
        {error && <div className="text-red-500 mb-4">{error}</div>}
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
          <Link to="/signup" className="text-sm underline">Sign Up</Link>
        </div>
        <Button variant="outline" className="border-2 border-black">
          <i className="fa-light fa-rocket-launch"></i>
        </Button>
      </Form>
    </main>
  )
}