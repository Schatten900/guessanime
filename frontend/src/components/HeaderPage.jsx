import { CircleUser } from "lucide-react";
import { House } from "lucide-react";
import { Medal } from "lucide-react";
import { useNavigate } from "react-router-dom";

function HeaderPage(props){

    const navigate = useNavigate();
    const redirectPages = (page) => {
        navigate(`/${page}`)
    }

    return (
        <div className="bg-blue-700 h-[65px] w-[100%] flex flex-row justify-between items-center p-8">
            <div className="flex flex-row gap-5">
                <img 
                className="rounded-2xl size-10"
                src={props.image} 
                alt="user-image"
                />
                <h1>{props.username}</h1>   
             </div>

             <div className="flex flex-row space-x-6">
                <CircleUser 
                onClick={()=>redirectPages("user")}
                size={50}
                color="white"
                cursor="pointer"
                /> 

                <Medal 
                onClick={()=>redirectPages("ranking")}
                size={50}
                color="white"
                cursor="pointer"
                />

                <House 
                onClick={()=>redirectPages("")}
                size={50}
                color="white"
                cursor="pointer"
                /> 
             </div>
        </div>
    )
}

export default HeaderPage;