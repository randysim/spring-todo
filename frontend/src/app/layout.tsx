"use client"
import { Inter } from "next/font/google";
import "./globals.css";
import UserProvider from "../components/context/UserProvider";
import NavBar from "@/components/ui/NavBar";

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
