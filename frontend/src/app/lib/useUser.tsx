import { useRouter } from "next/navigation";
import useSWR from "swr";

const fetcher = (url : string) => fetch(url, { method: "GET", credentials: "include"}).then((res) => res.json())

export default function useUser(required = false) {
    const { data, error } = useSWR("http://localhost:8080/api/v1/user/authenticated", fetcher);
    const router = useRouter();

    let value = { signedIn: false, loading: true, user: {} }

    if (error) {
        if(required) {
            router.push("/login");
        }

        return value;
    }

    if (data) {
        value.loading = false;
        value.signedIn = true;
        value.user = data;
    }

    return value;
}