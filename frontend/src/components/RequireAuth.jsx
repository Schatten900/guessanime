import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function RequireAuth(props){

    const navigate = useNavigate();

    useEffect(()=>{
        const token = sessionStorage.getItem("user");
        if (!token){
            navigate("/login");
        }

    },[navigate])

    return props.children;
}

export default RequireAuth;