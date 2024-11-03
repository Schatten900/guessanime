function Text(props){
    return (
        <h1 className={`font-serif text-2xl text-${props.color} text-pretty text-center`}>
            {props.children}
        </h1>
    )

}

export default Text;