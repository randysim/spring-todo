"use client"
import { Inter } from "next/font/google";
import "./globals.css";
import UserProvider from "../comps/context/UserProvider";
import NavBar from "@/comps/ui/NavBar";

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <head>
        <title>Todo List</title>
      </head>
      <body className={inter.className}>
          <UserProvider>
            <NavBar />
            <div className="pt-16">
              {children}
            </div>
          </UserProvider>
        </body>
    </html>
  );
}
