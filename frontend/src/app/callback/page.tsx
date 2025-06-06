"use client"

import {useEffect} from "react"
import auth from "@/Auth"
import { useRouter } from 'next/navigation'
import LoadingPage from "@/pages/loadingPage/loadingPage"
import Auth from "@/Auth"

export default function CallbackPage() {
    const router = useRouter()

    useEffect(() => {
        auth.handleAuthentication()
            .then(() => {
                console.log("redirecting to dashboard")
                router.push('/dashboard')
            })
            .catch((error) => {
                console.error("Error during authentication:", error);
                Auth.logout()
            });
    }, []);

    return (
        <LoadingPage text={"logging you in"}/>
    )
}