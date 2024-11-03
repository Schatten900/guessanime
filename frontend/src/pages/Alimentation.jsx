import {useState} from "react";
import Text from "../components/Text";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import SubmitButton from "../components/SubmitButton";

function Alimentation(){

    const [messageBackEnd,setMessage] = useState("");
    const [messageError, setErrorMessage] = useState("");
    const {showAlert, openAlert,closeAlert} = useAlert();

    const handleMessageOfBackEnd = async () => {
        console.log("Hi");
        try{
        
            const response = await fetch("http://localhost:5050/alimentation",{
                method:"POST",
                headers: {
                    "Content-Type":"application/json"
                },
                body: JSON.stringify({
                    "message":"ok"
                })
            });
            if (!response.ok){
                const data = await response.text();
                setErrorMessage(data.message || "Ocurred some error on alimentation");
                openAlert();
            }
            else{
                console.log("Message sent");
                setMessage(response.message);
            }
        }
        catch(error){
            setErrorMessage(error);
            openAlert();
        }
    }

    return (
        <div className="w-screen h-screen flex flex-col justify-center items-center bg-slate-100">
            <div className="flex flex-col justify-center"> 
            <SubmitButton onClick={handleMessageOfBackEnd}>Ask for animes</SubmitButton>
            <Text>{messageBackEnd}</Text>
            {showAlert && <Alert messageError={messageError} onClick={closeAlert}/>}

            </div>
        </div>
    )
}

export default Alimentation;