import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user')
        return storedUser ? JSON.parse(storedUser) : null
    });

    const login = async (user) => {
        setUser(user);
        localStorage.setItem('jwtToken', user.jwt)
        localStorage.setItem('user', JSON.stringify(user))
    };

    const logout = () => {
        setUser(null);
        localStorage.removeItem("jwtToken")
        localStorage.removeItem("user")
    };

    return <AuthContext.Provider value={{ user, login, logout }}>{children}</AuthContext.Provider>;
}

export const useAuth = () => {
    return useContext(AuthContext);
};
