import SmallButton from "./SmallButton";
import "../styles/Alert.css"  

function Alert(props){
    return (
        <div className="custom-alert-overlay">
            <div className="custom-alert-body">
                <p>{props.message ? props.message : ""}</p>
                <SmallButton 
                onClick={props.onClick}
                >
                    OK
                </SmallButton>
            </div>
        </div>
    )

}
export default Alert;