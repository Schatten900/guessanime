import LoginForm from "../components/LoginForm";
import '../styles/animations.css'

function Login(){
    return (
        <div className="background-animation w-screen h-screen flex flex-col justify-center items-center p-2 space-y-4  ">
        <LoginForm/>
    </div>
    )
}

export default Login;