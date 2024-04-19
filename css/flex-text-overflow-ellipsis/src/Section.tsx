import { faker } from "@faker-js/faker";
import Card from "./Card";

interface SectionProps {
  title: string;
  cardCount: number;
}

function Section({ title, cardCount }: SectionProps) {
  return (
    <section className="min-w-0">
      <h2 className="mb-2 text-2xl font-bold">{title}</h2>
      <div className="flex flex-col gap-4">
        {Array.from({ length: cardCount }).map((_, index) => (
          <Card
            key={index}
            title={faker.lorem.sentence()}
            description={faker.lorem.paragraph()}
          />
        ))}
      </div>
    </section>
  );
}

export default Section;
