import Section from "./Section";
import Sidebar from "./Sidebar";

function App() {
  return (
    <div className="flex min-h-screen">
      <Sidebar />
      <div className="flex-1 min-w-0 p-4">
        <h1 className="mb-4 text-3xl font-extrabold">
          Flex Text Overflow Ellipsis Demo
        </h1>

        <div className="flex [&>*]:w-1/2 gap-4 w-full">
          <Section title="Section 1" cardCount={3} />
          <Section title="Section 2" cardCount={4} />
          <Section title="Section 3" cardCount={5} />
        </div>
      </div>
    </div>
  );
}

export default App;
