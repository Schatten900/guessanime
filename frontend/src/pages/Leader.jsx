import HeaderPage from "../components/HeaderPage";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import { useEffect, useState } from "react";
import Text from "../components/Text";
import RankingPerfil from "../components/RankingPerfil";
import makima from '../assets/makima.jpg'

function Leader(){

    // ---------------- Data -----------------------//

    const [usersRank,setUserRanks] = useState([]);
    const [userData, setUserData] = useState(JSON.parse(sessionStorage.getItem("user")));

    useEffect(() => {
      const storedUser = JSON.parse(sessionStorage.getItem("user"));
      if (storedUser) {
        setUserData(storedUser);
      }
    }, [sessionStorage.getItem("user")]);

    // ---------------- Error ----------------------//
    const [messageError, setErrorMessage] = useState("");
    const { showAlert, openAlert, closeAlert } = useAlert();

    // --------------- Effec -----------------------//

    useEffect(()=>{
        takeUsersRanks();
    },[])

    // ---------------- Functions -----------------//

    const takeUsersRanks = async () => {
        //Lista com usuarios com maior pontos em ordem
        try{
            const response = await fetch("http://localhost:5050/ranking",{
                method:"GET",
                headers:{
                    "Content-Type":"application/json"
                }
            });

            if (!response.ok){
                const errorData = await response.json();
                setErrorMessage(errorData.message || "ocurried error with ranking");
                openAlert();
                return;
            }
            const data = await response.json();
            setUserRanks(data);
        }
        catch(error){
            setErrorMessage(error.message);
            openAlert();
        }
    }

    return (
        <div className="flex flex-col items-center justify-between h-screen ">
            <HeaderPage userData={userData} setUserData={setUserData}/>
            <div><Text> HALL OF FAME </Text></div>
            
            <div className=" w-full max-w-2xl h-96
             flex flex-col justify-center items-center space-y-4 overflow-y-auto
             rounded-lg shadow-md py-5 -mt-5 ">
                {usersRank.map((user)=>(
                    <RankingPerfil
                    key={user.id}
                    image={`data:image/jpg;base64,${user.image}`}
                    name={user.username}
                    points={user.points}
                    />
                ))}
            </div>

            <img
            src={makima} 
            alt="Personagem2"
            className="home-character-right-image"
            />

            {showAlert && <Alert messageError={messageError} onClick={closeAlert} />}
            
        </div>
    )
}

export default Leader;