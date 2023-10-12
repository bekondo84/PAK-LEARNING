 export const nameInitials = (value:string) =>{
   const temp = value?.trim()
   if(temp === undefined || temp === ""){
      return ""
   }
   const tempArray = temp.trimStart().toUpperCase().split(" ")
   return `${tempArray[0][0]}${(tempArray.length === 1)?"":tempArray[tempArray.length-1][0]}`
 }

 export const swapElements = (array:any, index1:number, index2:number) => {
   array[index1] = array.splice(index2, 1, array[index1])[0];
   return array
};

const englishMonth = ["January","February","March","April","May","June","July","August","September","October","November","December"];
const frenchMonth = ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"];
const englishDays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
const frenchDays = ['Dimanche','Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi']

export const translateDateTime = (value:string, separator:string, language:string) =>{
   if(value == null || value ===""){
      switch(language){
         case "fr":
            return "Aucune Date"
         case "en":
            return "No Date"
         default:
            return "Aucune Date"
      }
   }
   const dateArray = value.split(separator)[0].split("/")
   const time = value.split(separator)[1]
   const d = new Date(`${dateArray[2]}-${dateArray[1]}-${dateArray[0]}`)
   if(language === "fr"){
      const finalDateTime = `${frenchDays[d.getDay()]} ${d.getDate()} ${frenchMonth[d.getMonth()]} ${d.getFullYear()} à ${time}`
      return finalDateTime
   }
   if(language === "en"){
      const finalDateTime = `${englishDays[d.getDay()]} ${d.getDate()} ${englishMonth[d.getMonth()]} ${d.getFullYear()} at ${time}`
      return finalDateTime
   }
}

export const getDurationBtwDates = (date1:string, date2:string, separator:string, language:string) => {
   const date1Array = date1.split(separator)[0].split("/")
   const date2Array = date2.split(separator)[0].split("/")

   const d1 = new Date(`${date1Array[2]}-${date1Array[1]}-${date1Array[0]}`)
   const d2 = new Date(`${date2Array[2]}-${date2Array[1]}-${date2Array[0]}`)

   const difference = d2.getTime() - d1.getTime();
   const totalDays = Math.ceil(difference / (1000 * 3600 * 24))
   if(totalDays>1){
      return totalDays+(language === "fr"? "j" : "d")
   }
   const totalHours = Math.ceil(difference / (1000 * 3600))
   if(totalHours>1){
      return totalHours+"h"
   }
   const totalMinutes = Math.ceil(difference / (1000 * 60))
   if(totalMinutes>1){
      return totalMinutes+"mins"
   }else{
      return Math.ceil(difference / 1000)+"s"
   }
}

export const getGenderLabel = (gender:string) =>{
   switch(gender){
       case 'M':
           return "M."
       case 'F':
           return "Mme."
       default: return "M."
   }
}