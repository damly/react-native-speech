/**
 * Created by timekettle on 2018/5/14.
 */

import {
    NativeModules,
    Platform,
    NativeAppEventEmitter,
} from "react-native";

const SpeechTranslatorModule = NativeModules.SpeechTranslatorModule;

class SpeechTranslator {

    start(mode = 0) {
        SpeechTranslatorModule.start(mode);
    }

    stop() {
        SpeechTranslatorModule.stop();
    }
}

module.exports = new SpeechTranslator();