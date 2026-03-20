/**
 * Function debounce.
 * when an event is continuously triggered, the event handler will only execute if the event is not triggered again within a specified interval.
 * If the event is triggered again within that interval, the timer restarts.
 *
 * @param func Function
 * @param delay millisecond
 * @returns Function
 */
export function debounce(func: Function, delay = 200) {
  let timer: number;

  return function () {
    /**
     * Clearing the timer.if the event is triggered again within the specified delay time, the original timer will be cleared,
     * and the operation that the original timer was supposed to execute will not be carried out.
     */
    clearTimeout(timer);
    // Set a timer, delay execution.
    timer = setTimeout(func, delay);
  };
}

/**
 * Ref debounce
 *
 * @param value any
 * @param delay millisecond
 * @returns customRef
 */
export function debounceRef(value: any, delay = 200) {
  let timer: NodeJS.Timeout;

  return customRef((track, trigger) => {
    return {
      get() {
        track();
        return value;
      },
      set(newValue) {
        clearTimeout(timer);

        timer = setTimeout(() => {
          value = newValue;
          trigger();
        }, delay);
      },
    };
  });
}
