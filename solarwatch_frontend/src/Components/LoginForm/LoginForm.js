import { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import "../userForm.css";
import { useGetToken } from "../../Hook/Hook";
import { useAuth } from "../../AuthProvider";

function logInUser(user) {
    return fetch('/api/user/login', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
    }).then(res => {
        if (!res.ok) {
            return res.json().then(errorData => {
                alert("Login failed: " + errorData.error);
            });
        }
        return res.json();
    })
        .then(data => {
            const token = data.jwt;
            localStorage.setItem('jwtToken', token);
            return data;
        }).catch(error => {
            console.error("Login failed:", error.message);
            alert("An unexpected error occurred: " + error.message);
        })
}

export default function LoginForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const { login } = useAuth();

    const handleUserLogin = (e) => {
        e.preventDefault();
        const user = { username, password };
        logInUser(user)
            .then((user) => {
                login(user)
                navigate('/')
            })
    }

    const handleGoBack = () => {
        navigate("/")
    }

    if (error !== "") {
        return <p>{error}</p>
    }

    return (
        <div className="form-wrapper">
            <span className="form-circle1"></span>
            <span className="form-circle2"></span>
            <div className="formDiv">
                <form>
                    <h1 className="headTitle">Log In</h1>
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
                        <p className="linkToSignUp">Don't have an account? <Link className="linkToSignOrLogin" to={"/user/register"}>Sign up now</Link></p>
                    </div>
                    <div className="formBtnDiv">
                        <button className="formBtn backBtn" onClick={handleGoBack} type="button">Back</button>
                        <button className="formBtn login" type="submit" onClick={handleUserLogin}>Log In</button>
                    </div>
                </form>
            </div>
        </div>
    )
}