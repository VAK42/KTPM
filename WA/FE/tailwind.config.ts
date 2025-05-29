import type { Config } from 'tailwindcss'
import { mtConfig } from "@material-tailwind/react";

export default {
  content: [
    "./app/**/*.{js,jsx,ts,tsx}",
    "./node_modules/@material-tailwind/react/**/*.{js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {},
  },
  plugins: [mtConfig],
} satisfies Config;