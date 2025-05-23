"use client"

import Auth from "@/Auth"
import "./home.scss"
import Link from "next/link"

export default function Home() {

    return (
        <main className="home">
            <div className="content">
                <h1>Welcome!</h1>
                <p className="mainText">
                    This is an early alpha version of my shotlist creation tool. Log in to get started!
                </p>
                <button onClick={() => Auth.login()}>Log in</button>

                <p className="bottomText">
                    Be aware that multiple features are still missing. If you find any bugs or have suggestions, please
                    report them on <Link href="https://github.com/elYanuki/shotlist-tool/issues/new/choose" target={"_blank"}> the GitHub page</Link>.
                    Thank you! :)
                    Since this is an alpha release, I do <strong>NOT</strong> guarantee the safety of your data at this time.
                </p>
            </div>
        </main>
    );
}
