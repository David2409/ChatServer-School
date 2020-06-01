export interface User {
    id: String,
    username: String
    password: String
}

export const NullUser : User = { id: "NULl", username: "NULL", password: ""};