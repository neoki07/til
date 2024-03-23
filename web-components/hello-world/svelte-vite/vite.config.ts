import { resolve } from "path";
import { defineConfig } from "vite";
import { svelte } from "@sveltejs/vite-plugin-svelte";
import dts from "vite-plugin-dts";

// https://vitejs.dev/config/
export default defineConfig({
  build: {
    lib: {
      entry: resolve(__dirname, "src/main.ts"),
      formats: ["es"],
      fileName: "hello-world",
    },
    rollupOptions: {
      external: ["svelte"],
    },
    sourcemap: true,
  },
  plugins: [
    svelte({
      compilerOptions: {
        customElement: true,
      },
    }),
    dts(),
  ],
});
