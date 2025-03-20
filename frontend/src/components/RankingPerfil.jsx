import { Crown } from "lucide-react";

function RankingPerfil(props) {
  return (
    <div className="w-[90%] h-[30%] rounded-md transform hover:scale-105 hover:bg-slate-300 
        flex flex-row items-center px-4 justify-around">
      
      <div className="w-16 h-16 rounded-full overflow-hidden border-2 border-gray-500">
        <img 
          src={props.image} 
          alt="accountPicture" 
          className="w-full h-full object-cover"
        />
      </div>

      <div className="flex flex-col text-left">
        <p className="text-balance text-center font-medium text-black">{props.name}</p>
        <p className="text-blue-700">Points: {props.points}</p>
      </div>

      <div><Crown/></div>
      
    </div>
  );
}

export default RankingPerfil;
