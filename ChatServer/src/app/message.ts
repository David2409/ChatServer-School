export interface Message {
    id: String,
    serverId: String,
    roomId: String,
    from: String,
    msg: String,
    sendedAt: String
}

export const NullMessage : Message = {id: "null", serverId: "", roomId:"", from:"", msg:"", sendedAt:""};