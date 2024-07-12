import {InterestModel} from "./InterestModel.ts";

export type UserModel = {
    id : string,
    firstName: string,
    lastName: string,
    description: string,
    address: string,
    phoneNumber: string,
    gender: string,
    desiredGender: string,
    dateOfBirth: string,
    age: string,
    interests: InterestModel[]
}