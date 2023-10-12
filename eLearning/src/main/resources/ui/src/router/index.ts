import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const v2baseURL = '/e-learning'
 

const routes: Array<RouteRecordRaw> = [
 /** {
    path: '/e-learning',
    name: 'home',
    component: HomeView,
    children: [
       {
         path: 'my-learning',
         name: 'mylearining', // Formation en Groupes
         component: () => import('@/components/MyLearningComponent.vue')
       },{
        name: 'register',
        path: 'my-learning/register/:op',
        component: () => import('@/components/RegisterPanelComponent.vue')
      }, {
        name: 'activity',
        path: 'my-learning/activities/:op',  // Details de la Formation
        component: () => import('@/components/ActivityFormComponent.vue'),
      }, {
         name: 'inprogress', 
         path: 'my-learning-inprogress', // Formations en Cours
        component: () => import('@/components/MyLearningInprogressComponent.vue')
      },{
        name: 'test',
        path: 'my-learning/activities/:op/test/:activity',
        component: () => import('@/components/TestComponent.vue'),
      },{
        name: 'homework',
        path: 'my-learning/activities/:op/homework/:activity',
        component: () => import('@/components/HomeWorkComponent.vue'),
      }, {
        name: 'lecon',
        path: 'my-learning/activities/:op/lecon/:activity',
        component: () => import('@/components/LeconComponent.vue'),
      }, {
        name: 'done',
        path: 'my-learning-done', // Formation terminées
       component: () => import('@/components/MyLearningDoneComponent.vue')
     }, {
          name: 'online',
          path: 'my-learning-online', // Formations Disponible
        component: () => import('@/components/MyLearningOnlineComponent.vue')
      }, {        
          path: '/e-learning',
          redirect: '/e-learning/my-learning'
       }, {
          name: 'training',
          path:'my-learning/:op',  // Details de la Formation non souscrites
          component: () => import('@/components/TrainingFormComponent.vue') 
      }, {
        name: 'onlinetraining',
        path:'my-learning-online/:op',  // Details de la Formations Disponible 
        component: () => import('@/components/TrainingFormComponent.vue') 
    }
    ]
  },
  {
    path: '/e-learning/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
  },
  {
    path: '/e-learning/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import( '../views/AboutView.vue')
  },
  */
  
  // Version 2.0 Paths are included below
  {
    path: `${v2baseURL}`,
    redirect: {name: 'LoginPage'},
  },
  {
    path: `/`,
    redirect: {name: 'LoginPage'},
  },
  {
    path: `${v2baseURL}/login`,
    name: 'LoginPage',
    component: () => import('@/views/v2.0/LoginPage.vue')
  },
  {
    path: `${v2baseURL}/profil`,
    name: 'ProfilePage',
    component: () => import('@/views/v2.0/ProfilePage.vue')
  },
  {
    path: `${v2baseURL}/formations`,
    name: 'HomeLayout',
    component: () => import('@/views/v2.0/Home/Layout.vue'),
    children:[
      {
        path: '',
        name: 'HomePage',
        component: () =>import('@/views/v2.0/Home/Index.vue')
      },
      {
        path: `inscrites`,
        name: 'SubscribedCourse',
        component: () =>import('@/views/v2.0/Home/SubscribedCourse.vue')
      },
      {
        path: `enCours`,
        name: 'OngoingCourse',
        component: () =>import('@/views/v2.0/Home/OngoingCourse.vue')
      },
      {
        path: `terminees`,
        name: 'CompleteCourse',
        component: () =>import('@/views/v2.0/Home/CompleteCourse.vue')
      },
      {
        path: `details/:id/:groupCode`,
        name: 'CourseDetails',
        component: () =>import('@/views/v2.0/Home/CourseDetails.vue')
      }
    ]
  },
  {
    path: `${v2baseURL}/formations/:type/:id/presentation`,
    name: 'TraningLayout',
    component: () => import('@/views/v2.0/Training/TraningLayout.vue'),
    children:[
      {
        path: '',
        name: 'TrainingIndex',
        component: () =>import('@/views/v2.0/Training/TrainingIndex.vue'),
      },
      {
        path: 'evaluation/:testId',
        name: 'TrainingTest',
        component: () =>import('@/views/v2.0/Training/TrainingTest.vue'),
      },
      {
        path: `lecon/:lessonId`,
        name: 'LessonLayout',
        component: () =>import('@/views/v2.0/Training/Lesson/LessonLayout.vue'),
        children:[
          {
            path: '',
            name: 'LessonIndex',
            component: () =>import('@/views/v2.0/Training/Lesson/LessonIndex.vue'),
          },
          {
            path: 'ressources/:count',
            name: 'LessonRessource',
            component: () =>import('@/views/v2.0/Training/Lesson/LessonRessource.vue'),
          },
          {
            path: `cours-pdf/:courseId`,
            name: 'LessonPDF',
            component: () =>import('@/views/v2.0/Training/Lesson/LessonPDF.vue'),
          },
          {
            path: `cours-video/:courseId`,
            name: 'LessonVideo',
            component: () =>import('@/views/v2.0/Training/Lesson/LessonVideo.vue'),
          },
          {
            path: `evaluation/:testId`,
            name: 'LessonTest',
            component: () =>import('@/views/v2.0/Training/Lesson/LessonTest.vue'),
          }
        ]
      }
    ]
  },
  {
    path: `${v2baseURL}/formations/quiz/:lessonId/:testId`,
    name: 'LessonQuizPage',
    component: () =>import('@/views/v2.0/Training/LessonQuizPage.vue'),
  },
  {
    path: `${v2baseURL}/formations/quiz/:trainingId/:testId`,
    name: 'TrainingQuizPage',
    component: () =>import('@/views/v2.0/Training/TrainingQuizPage.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(){
    window.scrollTo(0,0)
  }
})

export default router
