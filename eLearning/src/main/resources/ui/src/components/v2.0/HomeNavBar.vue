<template>
    <div class="w-full flex items-center gap-2">
        <div class="w-full flex justify-start items-center gap-8">
            <span class="text-black text-[32px] font-bold font-myriadBold flex capitalize animate__animated animate__fadeIn">{{ title }}</span>
            <span class="bg-pakYellow w-[60px] h-[60px] flex justify-center items-center aspect-square p-4 font-bold font-centuryGothic rounded-full text-white text-xl">{{ trainingLength }}</span>
        </div>
        <div v-if="computedNavLinks.length===0" role="status" class="animate-pulse w-fit h-fit p-2 gap-2 flex flex-row justify-between border-2 border-gray-200 dark:border-gray-700 bg-white rounded-[32px]">
            <div class="w-[140px] p-3 flex justify-center items-center">
                <div class="w-[100px] h-5 p-2 rounded-[32px] bg-gray-200 dark:bg-gray-700"></div>
            </div>
            <div class="w-[140px] p-3 flex justify-center items-center">
                <div class="w-[100px] h-5 p-2 rounded-[32px] bg-gray-200 dark:bg-gray-700"></div>
            </div>
            <div class="w-[140px] p-3 flex justify-center items-center">
                <div class="w-[100px] h-5 p-2 rounded-[32px] bg-gray-200 dark:bg-gray-700"></div>
            </div>
            <div class="w-[140px] p-3 flex justify-center items-center">
                <div class="w-[100px] h-5 p-2 rounded-[32px] bg-gray-200 dark:bg-gray-700"></div>
            </div>
        </div>
        <nav v-else class="w-fit h-fit p-2 gap-2 flex flex-row justify-between border-2 border-pakDarkBlue rounded-[32px] bg-white">
            <router-link
                v-for="(navLink, index) in computedNavLinks"
                :key="index"
                :to="navLink.href"
                class="w-[140px] p-2 rounded-[32px] flex justify-center items-center text-[20px] font-prototype text-black hover:!text-white hover:!bg-pakDarkBlue"
            >
                {{ navLink.name }}
            </router-link>
        </nav>
    </div>
</template>

<script lang="ts">
    import 'animate.css';
    import store from '@/store';
    import UrlUtils from '@/services/urlUtils'
    import AxiosService from '@/services/axiosService';
    import {BackEndLinkType, NavLinkType} from '@/types/navBarLinks'
    
    const axiosService = new AxiosService();

    export default {
        name: "HomeNavBar",
        props:{
            title:{
                type: String,
                default: "Vos Formations"
            },
        },
        data(){
            return{
                defaultLinkList:[
                    {
                        name: this.translator('my.learning.available.label'),
                        href: {name: "HomePage"}
                    },
                    {
                        name: this.translator('my.learning.subscribed.label'),
                        href: {name: "SubscribedCourse"}
                    },
                    {
                        name: this.translator('my.learning.inprogress.label'),
                        href: {name: "OngoingCourse"}
                    },
                    {
                        name: this.translator('my.learning.done.label'),
                        href: {name: "CompleteCourse"}
                    }
                ],

                // Integreated Data
                linkList:[],
                navLinkList:[], // Contains the updated Links for the navbar with their totals
                currentLinkTotal:0,
                translateHeaders: null,
            }
        },
        methods: {
            assignPageToLink(value:string, total:number){
                switch(value){
                    case 'my-learning-online':  // Disponible
                        return {
                                name: this.translator('my.learning.available.label'),
                                href: {name: "HomePage"},
                                total: total
                            }
                    case 'my-learning':  // Inscrites
                        return {
                                name: this.translator('my.learning.subscribed.label'),
                                href: {name: "SubscribedCourse"},
                                total: total
                            }
                    case 'my-learning-inprogress':  // Encours
                        return {
                                name: this.translator('my.learning.inprogress.label'),
                                href: {name: "OngoingCourse"},
                                total: total
                            }
                    case 'my-learning-done':  // Terminées
                        return {
                                name: this.translator('my.learning.done.label'),
                                href: {name: "CompleteCourse"},
                                total: total
                            }
                }
            },
            async getHomeCms() {
                this.session = Object.assign({}, store.getters.getCurrentSession);
                let homecms = await axiosService.getHomeComponent();
                this.linkList.splice(0, this.linkList.length);
                this.linkList.push(...homecms.menuitems)
                
                let tempProfile = this.linkList
                let temp = tempProfile[tempProfile.length-1]

                tempProfile.splice(tempProfile.length-1,1)
                tempProfile.splice(0,0,temp)
                this.linkList = [...tempProfile]

                this.navLinkList = this.linkList.map((link:BackEndLinkType)=>{
                    return this.assignPageToLink(link.code,link.total)
                })
                this.trainingLength = this.navLinkList
            },
            translator(key: string) {
                if (this.translateHeaders != null) {
                    return this.translateHeaders[key];
                }
                return key;
            },
        },
        async created() {
            this.translateHeaders = await axiosService.translate(['my.learning.available.label', 'my.learning.subscribed.label', 'my.learning.inprogress.label', 'my.learning.done.label']);
            this.emitter.on(UrlUtils.HOMECMS_REFRESH_EVENT, await this.getHomeCms);
            this.emitter.on("authorization-error", ()=>{this.$router.push({name: 'LoginPage'})});
        },
        async mounted() {
            await this.getHomeCms();
            this.timer = setInterval(() => {
                this.getHomeCms();
            }, 60000)
        },
        computed:{
            trainingLength:{
                set(navLink:Array<NavLinkType>){
                    this.currentLinkTotal = navLink.find((link:NavLinkType)=>{
                        return link.href.name === this.$route.name
                    })?.total
                },
                get(){
                    return this.currentLinkTotal
                }
            },
            computedNavLinks(){
                return this.navLinkList.length>0?this.navLinkList:[]
            }
        },
    }
</script>

<style scoped>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;
    
    @layer base{
        a.router-link-exact-active{
            @apply !text-white;
            @apply !bg-pakDarkBlue
        }
    }
</style>