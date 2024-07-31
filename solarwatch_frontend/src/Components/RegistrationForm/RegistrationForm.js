import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../userForm.css"

function createUser(user) {
    console.log(user);
    return fetch('/user/register', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
    }).then((res) => res.json());
}

export default function RegistrationForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleUserRegistration = (e) => {
        e.preventDefault();
        const user = { username, password };
        createUser(user)
            .then(navigate('/user/login'))
    }

    const handleGoBack = () => {
        navigate("/")
    }

    return (
        <div className="form-wrapper">
            <span className="form-circle1"></span>
            <span className="form-circle2"></span>
            <div className="formDiv">
                <form onSubmit={handleUserRegistration}>
                    <h1 className="headTitle">Registration</h1>
                    <div className="input-box">
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            value={username}
                            placeholder="Username"
                            required
                            onChange={(e) => setUsername(e.target.value)}
                            id="username"
                        />
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            value={password}
                            placeholder="Password"
                            required
                            onChange={(e) => setPassword(e.target.value)}
                            id="password"
                        />
                        <hr></hr>
                    </div>
                    <button className="formBtn createAccBtn" type="submit">Create An Account</button>
                    <button className="formBtn backBtn" onClick={handleGoBack} type="button">Back</button>
                    <p className="linkToLogin">Already have an account?<Link className="linkToSignOrLogin" to={"/user/login"}>Log in</Link></p>
                </form>
            </div>
        </div>
    )
}