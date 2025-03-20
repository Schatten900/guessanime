import Text from "./Text";
import SubmitButton from "./SubmitButton";
import { Frown, Smile } from "lucide-react";

function EndGame(props) {
  return (
    <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white rounded-lg p-8 z-20">
      <div className="flex flex-col items-center space-y-4">
        {props.text === "You win!" ? (
          <Smile size={48} className="text-green-500" />
        ) : (
          <Frown size={48} className="text-red-500" />
        )}
        <Text>{props.text}</Text>
        {props.text === "You win!" ? (
        <p className="text-green-500 font-mono text-xl items-center">Reward 25 points</p>
        ) : (
        <p className="text-red-500 font-mono text-xl items-center">Loss 10 points</p>
        )}
        <div className="flex flex-row space-x-4">
          <SubmitButton onClick={props.onTryAgain}>Try Again</SubmitButton>
          <SubmitButton onClick={props.onExit}>Exit</SubmitButton>
        </div>
      </div>
    </div>
  );
}

export default EndGame;