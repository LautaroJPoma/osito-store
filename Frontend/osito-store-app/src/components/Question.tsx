type QuestionProps = {
  question: string;
  answer: string;
};

export default function Question({ question, answer }: QuestionProps) {
  return (
    <div className="mb-4 p-4 border rounded-lg shadow-sm bg-white">
      <h3 className="text-lg font-semibold text-blue-500">{question}</h3>
      <p className="text-gray-700 mt-2">{answer}</p>
    </div>
  );
}
