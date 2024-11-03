function SmallButton(props){
    return (
        <button
        className="w-[100px] h-[30px] rounded-lg bg-black text-white font-bold transition duration-300 hover:scale-110"
        onClick={props.onClick}
        >
            {props.children}
        </button>
    )
}

export default SmallButton;