import axios from "axios"
import store from "@/store";

import UrlUtils from "./urlUtils";
import { emitter } from "./emitter";


export default class AxiosService {
    
    errorHandler(err: any) {
        if (err != null && err.response != null 
            && err.response.status == '401') {
            emitter.emit("authorization-error")
        } else {
            throw err ;
        }
    }

    async translate(keys: string[]) {
        const url = UrlUtils.I18N_URL+keys.join(',');
        return (await axios.get(url)).data;
    }
    async getLoginComponent() {
        const response = (await axios.get(UrlUtils.LOGIN_CMS)).data;
        store.commit("setGeneral", response);
        return response;
    }
    async authenticate(username: string, password: string) {
        const request = {username: username, password: password};
        const response = ( (await axios.post(UrlUtils.AUTHENTICATE_URL, request))).data;
        axios.defaults.headers.common['Authorization'] = 'Bearer '+response['access_token'];
        store.commit("setCurrentSession", response.user);
        return response;
    }

    async getHomeComponent() {
        try {
            const response = (await axios.get(UrlUtils.HOME_CMS)).data;
           return response;
        } catch (error) {
            this.errorHandler(error);
        } 
    }

    async getMyLearning() {
        try {
            const response = (await axios.get(UrlUtils.MY_LEARNING_URL)).data;
            return response ;
        } catch (error) {
            this.errorHandler(error);
        }
        
    }
    async getGroupsForTraining(training: any) {
        try {
             const response = (await axios.get(UrlUtils.REGISTER_GROUP_URL+training)).data;
             return response;
        } catch (error) {
            this.errorHandler(error);
        }
       
    }
    async registerForTraining(involve: number, group: string) {
        try {
            (await axios.post(UrlUtils.REGISTER_URL, {involve: involve, group: group}));
        } catch (error) {
            this.errorHandler(error);
        }
        
    }

    async startTraining(involve: number) {
        try {
            (await axios.post(UrlUtils.START_TRAINING_URL, {involve: involve}));
        } catch (error) {
            this.errorHandler(error);
        }
        
    }
   
    async unregisterForTraining(involve: number, group: string) {
        try {
            (await axios.post(UrlUtils.UNREGISTER_URL, {involve: involve, group: group}));
        } catch (error) {
            this.errorHandler(error);
        }
       
    }

    async getDoneTraining() {
        try {
            const response = (await axios.get(UrlUtils.DONE_TRAINING_URL)).data;
            return response;
        } catch (error) {
            this.errorHandler(error);
        }    
    }

    async getOnlineTraining() {
        try {
            return (await axios.get(UrlUtils.ONLINE_TRAINING)).data;
        } catch (error) {
            this.errorHandler(error);
        }   
    }

    async registerTrainings() {
        try {
            const response = (await axios.get(UrlUtils.REGISTER_TRAINIGS_URL)).data;
            return response;
        } catch (error) {
            this.errorHandler(error);
        }   
    }

    async getTraining(code: string) {
        try {
            return (await axios.get(UrlUtils.TRAINING_URL+code)).data;
        } catch (error) {
            this.errorHandler(error);
        }  
    }

    async registerOnlineTraining(code: string) {
        try {
            const URL = UrlUtils.REGISTER_ONLINE_TRAINING+code;
            return (await axios.post(URL, null)).data;
        } catch (error) {
            this.errorHandler(error);
        }     
    }
    async getResourceAsStream(pk: number, filename: string) {
        const url = UrlUtils.RESOURCE_URL_DOWNLOAD+pk+"/"+filename;
        const config: any = {
            responseType: 'blob'
         }
         return (await axios.get(url, config)).data;
    }

    async download(pk: number, filename: string) {
        const url = UrlUtils.RESOURCE_URL_DOWNLOAD+pk+"/"+filename;
        const config: any = {
            responseType: 'blob'
         }
         const response = (await axios.get(url, config)).data;
         const FILE = window.URL.createObjectURL(new Blob([response]));
         const docUrl = document.createElement('a');
         docUrl.href = FILE;
         docUrl.setAttribute('download', filename);
         document.body.appendChild(docUrl);
         docUrl.click();
         docUrl.remove();
    }

    async startActivity(code: string) {
        try {
            (await axios.post(UrlUtils.START_ACTIVITY_URL+code)).data;
        } catch (error) {
            this.errorHandler(error);
        }
        
    }

    async testResult(code : string) {
        try {
            return (await axios.get(UrlUtils.TESTRESULT_URL+code)).data;
        } catch (error) {
            this.errorHandler(error);
        }
        
    }

    async homeworkResult(code: string) {
        try {
            return (await axios.get(UrlUtils.HOMEWORKRESULT_URL+code)).data;
        } catch (error) {
            this.errorHandler(error);
        }    
    }

    async leconResult(code: string) {
        try {
            return (await axios.get(UrlUtils.LECONRESULT_URL+code)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async evaluateOpenQuestion(item: any) {
        try {
            const url = UrlUtils.EVALUATE_OPEN_URL;
            const request = {pk: item.pk, answer: item.answer}
            return (await axios.put(url, request)).data;
        } catch (error) {
            this.errorHandler(error);
        }
        
    }
    
    async evaluateTrueOrFalse(answer: string, question: any) {
        try {
            const url = UrlUtils.EVALUATE_TRUEORFALSE_URL+answer+"/"+question;
            return (await axios.put(url, null)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async submitEvaluation(lessonPk: number){
        try{
            const url = UrlUtils.SUBMIT_TEST_URL+lessonPk;
            return (await axios.put(url, null)).data;
        }catch(error){
            this.errorHandler(error);
        }
    }

    async getCourse(code: string, activity: string) {
        try {
            const url = UrlUtils.COURSE_BY_CODE+activity+"/"+code;
            return  (await axios.get(url)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }
    async evaluateMulti(answer: string, question: any) {
        try {
            const url = UrlUtils.EVALUATE_MULTI_URL+answer+"/"+question;
            return (await axios.put(url, null)).data;
        } catch (error) {
            this.errorHandler(error);
        } 
    }

    async markTestAsDone(test: number) {
        try {
            const URL = UrlUtils.MARK_TEST_ASDONE_URL+test;
            return (await axios.put(URL, null)).data;
        } catch (error) {
            this.errorHandler(error);
        }
        
    }

    async markHomeworkAsDone(homework: any) {
        try {
            const URL = UrlUtils.MARK_HOMEWORK_ASDONE_URL;
            return (await axios.post(URL, homework)).data;
        } catch (error) {
            this.errorHandler(error);
        } 
    }

    async nextStepCourse(pk: number) {
        try {
            const URL = UrlUtils.COURSE_STEP+'next-step/'+pk;
            return (await axios.put(URL, null)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async previousStepCourse(pk: number) {
        try {
            const URL = UrlUtils.COURSE_STEP+'previous-step/'+pk;
            return (await axios.put(URL, null)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async closeLeconActivity(pk: number) {
        try {
            const URL = UrlUtils.LECON_RESULT_CLOSE+pk;
            return (await axios.put(URL, null)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async getEvaluationMark(question: number, itemPk: number) {
        try {
            const URL = UrlUtils.EVALUATION_MARK_URL+question+'/'+itemPk;
            return (await axios.get(URL)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async getEvaluation(question: number, itemPk: number) {
        try {
            const URL = UrlUtils.EVALUATION_URL+question+'/'+itemPk;
            return (await axios.get(URL)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async isPasswordMatch(password: string) {
        try {
            const URL = UrlUtils.PASSWORD_MARCHER+password;
            return (await axios.get(URL)).data ;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async resetPassword(requestRegister: any) {
        try {
            const URL = UrlUtils.PASSWORD_RESET;
            return (await axios.post(URL, requestRegister)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async updateProfile(userData: any) {
        try {
            const URL = UrlUtils.UPDATE_PROFILE;
            return (await axios.put(URL, userData)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }

    async sendRequest(request: any) {
        try {
            const URL = UrlUtils.SEND_REQUEST;
            return (await axios.post(URL, request)).data;
        } catch (error) {
            this.errorHandler(error);
        }
    }
}