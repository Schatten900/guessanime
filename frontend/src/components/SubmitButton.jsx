function SubmitButton(props){
    return (
        <button
        className="w-[200px] h-[40px] rounded-lg bg-black text-white font-bold transition duration-300 hover:scale-105"
        onClick={props.onClick}
        >
            {props.children}
        </button>
    )
}

export default SubmitButton;