import { defineConfig } from "tsup";

export default defineConfig([
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: false,
    dts: true,
    sourcemap: true,
  },
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: true,
    dts: false,
    sourcemap: true,
    outExtension: () => ({
      js: ".min.js",
    }),
  },
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: false,
    dts: false,
    sourcemap: true,
    noExternal: ["lit"],
    outExtension: () => ({
      js: ".noExternal.js",
    }),
  },
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: true,
    dts: false,
    sourcemap: true,
    noExternal: ["lit"],
    outExtension: () => ({
      js: ".noExternal.min.js",
    }),
  },
]);
