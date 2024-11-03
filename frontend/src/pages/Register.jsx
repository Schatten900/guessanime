import RegisterForm from "../components/RegisterForm";
import '../styles/animations.css'

function Register(){
    return (
        <div className="background-animation w-screen h-screen flex flex-col justify-center items-center p-2 space-y-4  ">
            <RegisterForm/>
        </div>
    )
}

export default Register;