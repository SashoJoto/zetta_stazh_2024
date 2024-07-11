import {interestModel} from "./InterestModel";

export type userModel = {
    id : string,
    first_name: string,
    last_name: string,
    description: string,
    address: string,
    phoneNumber: string,
    gender: string,
    desired_gender: string,
    date_of_birth: string,
    age: string,
    interests: interestModel[]
}