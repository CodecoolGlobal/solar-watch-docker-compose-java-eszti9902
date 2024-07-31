import { useNavigate } from "react-router-dom"
import "./mainPage.css";
import { useGetToken } from "../../Hook/Hook.js"
import { useAuth } from "../../AuthProvider.jsx";

export default function MainPage() {
    const navigate = useNavigate();
    const token = useGetToken();
    const { logout } = useAuth();

    const registerNewUser = () => {
        navigate('/user/register')
    }

    const loginUser = () => {
        navigate('/user/login')
    }

    const searchForSolarData = () => {
        navigate('/solar-watch')
    }


    const logOutUser = () => {
        console.log(localStorage.getItem("jwtToken"));
        localStorage.removeItem("jwtToken")
        localStorage.removeItem("user")
        logout();
        console.log(localStorage.getItem("jwtToken"));
    }

    return (
        <div className="wrapper">
            <div className="mainpage-circle"></div>
            <h1 className="title">Solar-Watch</h1>
            <button className="solarBtn" onClick={searchForSolarData}>Search for solar data</button>
            <button className="registerBtn" onClick={registerNewUser}>Register!</button>
            <button className="loginBtn" onClick={loginUser}>Log In</button>
            <button className="logOutBtn" onClick={logOutUser}>Log Out</button>
        </div>
    )
}