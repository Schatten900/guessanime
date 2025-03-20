import "../styles/Fonts.css"

function Title(props){
    return (
        <h1 className="text-gray font-light text-4xl">
            {props.children}
        </h1>
    )

}

export default Title;