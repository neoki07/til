interface CardProps {
  title: string;
  description: string;
}

function Card({ title, description }: CardProps) {
  return (
    <div className="flex flex-col min-w-0 gap-1 p-4 border rounded-lg">
      <h3 className="text-lg font-bold truncate">{title}</h3>
      <p className="text-sm text-gray-500">{description}</p>
    </div>
  );
}

export default Card;
