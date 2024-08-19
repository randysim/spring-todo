"use client"
import { Inter } from "next/font/google";
import "./globals.css";
import UserProvider from "../comps/context/UserProvider";

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
            {children}
          </UserProvider>
        </body>
    </html>
  );
}
