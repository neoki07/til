<svelte:options customElement="hello-world" />

<script lang="ts">
  import { createEventDispatcher } from "svelte";
  const dispatch = createEventDispatcher();

  export let name = "World";
  export let count = 0;

  const _onClick = () => {
    count++;
    dispatch("count-changed");
  };

  export const sayHello = (name: string) => `Hello, ${name}`;
</script>

<h1>{sayHello(name)}!</h1>
<button on:click={_onClick} part="button">
  Click Count: {count}
</button>
<slot />

<style>
  :host {
    display: block;
    border: solid 1px gray;
    padding: 16px;
    max-width: 800px;
  }
</style>
