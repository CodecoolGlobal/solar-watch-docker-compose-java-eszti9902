import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthProvider"

export const ProtectedRoute = ({ children, role }) => {
    const { user } = useAuth();
    if (!user) {
        return <Navigate to={"/user/login"} />
    }
    console.log(user.role)
    console.log(role)
    if (user.role !== role) {
        return <Navigate to={"/"} />
    }
    return children;
}