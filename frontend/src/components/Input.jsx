function Input(props){
    return (
        <input 
        className="w-[80%] h-[40px] rounded-lg shadow-sm bg-slate-50 text-center transition duration-300 hover:scale-110 outline-none"
        name={props.name}
        type={props.type} 
        placeholder={props.placeholder}
        value={props.value}
        onChange={props.onChange}
        />
    )
}

export default Input;