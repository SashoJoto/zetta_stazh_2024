import {interestModel} from "./InterestModel";

export type userModel = {
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
    interests: interestModel[]
}