import "../styles/Fonts.css"

function Link(props){
    return (
        <h1 className=" text-sd font-serif text-blue-600 underline cursor-pointer"
        onClick={props.onClick}
        >
            {props.children}
        </h1>
    )
}

export default Link;