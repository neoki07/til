import { resolve } from "path";
import { defineConfig } from "vite";

export default defineConfig({
  build: {
    lib: {
      entry: resolve(__dirname, "src/hello-world.ts"),
      formats: ["umd"],
      name: "HelloWorld",
      fileName: () => "hello-world.cdn.js",
    },
    sourcemap: true,
  },
});
